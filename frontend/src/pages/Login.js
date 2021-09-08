import styled from 'styled-components'

export default function Login() {
  return (
    <Wrapper>
      <h1> Login</h1>
      <input type="Text" />
      <input type="Text" />
      <button type="submit">login</button>
    </Wrapper>
  )
}

const Wrapper = styled.form`
  display: grid;
  height: 100vh;
  grid-template-rows: 4fr 1fr 1fr 1fr 4fr;

  justify-content: center;
  justify-items: center;
  align-items: center;

  background-color: lightgreen;
`
