import styled from "styled-components";

export default function InputField({
  type,
  name,
  value,
  onChange,
  placeholder,
}) {
  return (<Wrapper
        type={type}
        name={name}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
      />
  )
}

const Wrapper = styled.input`
    margin: 5px;
`
