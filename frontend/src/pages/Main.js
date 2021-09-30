import styled from 'styled-components'
import { useState } from 'react'
import { addFood, addPain } from '../service/AxiosService'
import { useAuth } from '../auth/AuthProvider'
import Content from '../components/Content'
import InputField from '../components/InputField'
import Button from '../components/Button'
import Header from '../components/Header'
import Footer from '../components/Footer'

export default function Main() {
  const { token, user } = useAuth()
  const [food, setFood] = useState({ foodName: '' })
  const [pain, setPain] = useState({ painLevel: '' })
  const [error, setError] = useState()

  const foodHandler = event =>
    setFood({
      foodName: event.target.value,
    })

  const painHandler = event =>
    setPain({
      painLevel: event.target.value,
    })

  const foodSubmitHandler = event => {
    event.preventDefault()
    addFood(food, token)
      .then(response => console.log(response))
      .catch(error => setError(error))
      .finally(() => setFood({ foodName: '' }))
  }

  const painSubmitHandler = event => {
    event.preventDefault()
    addPain(pain, token)
      .then(response => console.log(response))
      .catch(error => console.error(error))
      .finally(() => setPain({ painLevel: "" }))
  }

  return (
    <Wrapper>
      <Header title="Inspector´s Notes" />
      <Content>
        <h1>Hello {user.username}, how do you feel?</h1>
        <form className="form1" onSubmit={foodSubmitHandler}>
          <InputField
            type="text"
            value={food.foodName}
            onChange={foodHandler}
            placeholder="eaten food"
          />
          <Button type="submit">save</Button>
        </form>
        <form className="form2" onSubmit={painSubmitHandler}>
          <InputField
            type="number"
            value={pain.painLevel}
            onChange={painHandler}
            placeholder="experienced pain"
            max = "10"
            min="1"
          />
          <Button type="submit">save</Button>
        </form>
        {error && <p className="error">{error.response.data.message}</p>}
      </Content>
      <Footer />
    </Wrapper>
  )
}

const Wrapper = styled.div`
  
  position: fixed;
  
  h1 {
    grid-column: 2;
    grid-row: 1;
  }

  .form1 {
    grid-column: 2;
    grid-row: 2;
    display: flex;
    align-items: center;
  }

  .form2 {
    grid-column: 2;
    grid-row: 3;
    display: flex;
    align-items: center;
  }
  
  .error {
    grid-column: 2;
    grid-row: 4;
  }
`
