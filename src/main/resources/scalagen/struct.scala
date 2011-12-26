// ----- {{name}}

object {{name}} extends ThriftStructCodec[{{name}}] {
  val STRUCT_DESC = new TStruct("{{name}}")
{{#fields}}
  val {{fieldConst}} = new TField("{{name}}", TType.{{constType}}, {{id}})
{{/fields}}

  val decoder = { (_iprot: TProtocol) =>
    var _field: TField = null
{{#fields}}
    var `{{name}}`: {{scalaType}} = {{defaultReadValue}}
    {{#required}}var _got_{{name}} = false{{/required}}
{{/fields}}
    var _done = false
    _iprot.readStructBegin()
    while (!_done) {
      _field = _iprot.readFieldBegin
      if (_field.`type` == TType.STOP) {
        _done = true
      } else {
        _field.id match {
{{#fields}}
{{reader}}
{{/fields}}
          case _ => TProtocolUtil.skip(_iprot, _field.`type`)
        }
        _iprot.readFieldEnd()
      }
    }
    _iprot.readStructEnd()
{{#fields}}
{{#required}}
    if (!_got_{{name}}) throw new TProtocolException("Required field '{{name}}' was not found in serialized data for struct {{struct}}")
{{/required}}
{{/fields}}
    {{name}}({{fieldNames}})
  }

  val encoder = { (_item: {{name}}, _oproto: TProtocol) => _item.write(_oproto) }

{{#big}}
  def apply({{applyParams}}) = new {{name}}({{ctorArgs}})
{{/big}}
}

{{^big}}case {{/big}}class {{name}}({{fieldParams}}) extends {{parentType}} {{#big}}
with Product with java.io.Serializable {{/big}}{
  import {{name}}._

{{#optionalDefaults}}
  def {{name}}OrDefault = {{name}} getOrElse {{value}}
{{/optionalDefaults}}

  override def write(_oprot: TProtocol) {
    validate()
    _oprot.writeStructBegin(STRUCT_DESC)
{{#fields}}
{{writer}}
{{/fields}}
    _oprot.writeFieldStop()
    _oprot.writeStructEnd()
  }

  def validate() = true //TODO: Implement this

{{#big}}
  def copy({{copyParams}}) =
    new {{name}}({{ctorArgs}})

  override def equals(other: Any): Boolean = runtime.ScalaRunTime._equals(this, other)

  override def hashCode: Int = runtime.ScalaRunTime._hashCode(this)

  override def toString: String = runtime.ScalaRunTime._toString(this)

  def canEqual(other: Any) = other.isInstanceOf[{{name}}]

  def productArity = {{arity}}

  def productElement(n: Int): Any = n match {
{{#fields}}
    case {{index}} => `{{name}}`
{{/fields}}
  }

  override def productPrefix = "{{name}}"
{{/big}}
}
