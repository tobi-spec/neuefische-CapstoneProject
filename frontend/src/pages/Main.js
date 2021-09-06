import styled from 'styled-components'
import axios from 'axios'
import { useState } from 'react'

export default function Main() {
  const [food, setFood] = useState('')

  const foodHandler = event =>
    setFood({
      foodName: event.target.value,
    })

  function addFood() {
    axios
      .post('/api/food', food)
      .then(response => console.log(food))
      .catch(error => console.error(error))
  }

  return (
    <section>
      <Wrapper>
        <h1>How do you feel? :)</h1>
        <p>I have eaten:</p>
        <input type="text" onChange={foodHandler} />
        <button onClick={addFood}>send</button>
      </Wrapper>
    </section>
  )
}

const Wrapper = styled.div`
  display: grid;
  height: 100vh;
  grid-template-rows: 4fr 1fr 1fr 1fr 4fr;

  justify-content: center;
  justify-items: center;
  align-items: center;

  background-color: lightgreen;
`