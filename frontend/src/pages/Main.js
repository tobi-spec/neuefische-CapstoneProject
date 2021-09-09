import styled from 'styled-components'
import axios from 'axios'
import { useState } from 'react'
import { addFood } from '../service/AxiosService'
import { useAuth } from '../auth/AuthProvider'

export default function Main() {
  const { token } = useAuth()
  const [food, setFood] = useState({ foodName: '' })

  const foodHandler = event =>
    setFood({
      foodName: event.target.value,
    })

  const submitHandler = event => {
    event.preventDefault()
    addFood(food, token)
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
