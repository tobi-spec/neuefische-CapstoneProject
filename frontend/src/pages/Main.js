import styled from 'styled-components'
import axios from 'axios'
import { useState } from 'react'

export default function Main() {
  const [food, setFood] = useState({ foodName: '' })

  const foodHandler = event =>
    setFood({
      foodName: event.target.value,
    })

  const submitHandler = event => {
    event.preventDefault()
    axios
      .post('/api/food', food)
      .then(response => console.log(response))
      .catch(error => console.error(error))
      .finally(() => setFood({ foodName: '' }))
  }

  return (
    <section>
      <Wrapper onSubmit={submitHandler}>
        <h1>How do you feel? :)</h1>
        <p>I have eaten:</p>
        <input type="text" value={food.foodName} onChange={foodHandler} />
        <button type="submit">send</button>
      </Wrapper>
    </section>
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
