package sbt.internal.util

import sbt.internal.util.Types.:+:

import sjsonnew.{ Builder, deserializationError, JsonFormat, Unbuilder }
import sjsonnew.BasicJsonProtocol, BasicJsonProtocol.asSingleton

import java.io.{ ByteArrayInputStream, ByteArrayOutputStream, InputStream, OutputStream }

trait HListFormat {
  implicit def HConsFormat[H: JsonFormat, T <: HList: JsonFormat]: JsonFormat[H :+: T] =
    new JsonFormat[H :+: T] {
      override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): H :+: T =
        jsOpt match {
          case Some(js) =>
            unbuilder.beginObject(js)
            val h = unbuilder.readField[H]("h")
            val t = unbuilder.readField[T]("t")
            unbuilder.endObject()

            HCons(h, t)

          case None =>
            deserializationError("Expect JValue but found None")
        }

      override def write[J](obj: H :+: T, builder: Builder[J]): Unit = {
        builder.beginObject()
        builder.addField("h", obj.head)
        builder.addField("t", obj.tail)
        builder.endObject()
      }
    }

  implicit val HNilFormat: JsonFormat[HNil] = asSingleton(HNil)

}

trait StreamFormat { self: BasicJsonProtocol =>
  def streamFormat[T](write: (T, OutputStream) => Unit, read: InputStream => T): JsonFormat[T] = {
    lazy val byteArrayFormat = implicitly[JsonFormat[Array[Byte]]]
    val toBytes = (t: T) => { val bos = new ByteArrayOutputStream(); write(t, bos); bos.toByteArray }
    val fromBytes = (bs: Array[Byte]) => read(new ByteArrayInputStream(bs))

    new JsonFormat[T] {
      override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): T =
        fromBytes(byteArrayFormat.read(jsOpt, unbuilder))

      override def write[J](obj: T, builder: Builder[J]): Unit =
        byteArrayFormat.write(toBytes(obj), builder)
    }
  }
}
