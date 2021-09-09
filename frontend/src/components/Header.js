import styled from 'styled-components'

export default function Header({ title }) {
  return (
    <Wrapper>
      <h1>{title}</h1>
    </Wrapper>
  )
}

const Wrapper = styled.header`
  background-color: #138808;
  display: flex;
  justify-content: center;
  align-items: center;
`
