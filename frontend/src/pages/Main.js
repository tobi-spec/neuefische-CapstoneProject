import styled from 'styled-components'

export default function Main() {
  return (
    <section>
      <Wrapper>
        <h1>How do you feel?</h1>
        <p>I have eaten:</p>
        <input type="text" />
      </Wrapper>
    </section>
  )
}

const Wrapper = styled.div`
  display: grid;
  height: 100vh;

  grid-template-rows: 4fr 1fr 1fr 4fr;

  justify-content: center;
  justify-items: center;
  align-items: center;
`
