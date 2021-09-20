import styled from 'styled-components'
import BackButton from "./BackButton";

export default function Header({ title }) {
  return (
    <Wrapper>
        <BackButton/>
      <h1>{title}</h1>
    </Wrapper>
  )
}

const Wrapper = styled.header`
  background-color: #138808;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  justify-items: center;
  align-items: center;
  
  
`
