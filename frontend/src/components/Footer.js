import styled from 'styled-components'

export default function Footer() {
  return (
    <Wrapper>
      <p>Intestinal Inspector</p>
    </Wrapper>
  )
}

const Wrapper = styled.footer`
  background-color: #138808;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
`
