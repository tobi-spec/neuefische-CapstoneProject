import styled from "styled-components";

export default function InputField({
  type,
  name,
  value,
  onChange,
  placeholder,
  min,
  max
}) {
  return (<Wrapper
        type={type}
        name={name}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        min={min}
        max={max}
      />
  )
}

const Wrapper = styled.input`
    width: 153px;
    height: 21px;
    margin:  5px;
`
