import styled from 'styled-components'
import { useState } from 'react'
import { addFood } from '../service/AxiosService'
import { useAuth } from '../auth/AuthProvider'
import Content from '../components/Content'
import InputField from '../components/InputField'
import Button from '../components/Button'
import Header from '../components/Header'
import Footer from '../components/Footer'

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
    <Wrapper>
      <Header title="Intestinal Inspector" />
      <Content>
        <h1>How do you feel? :)</h1>
        <form className="form1" onSubmit={submitHandler}>
          <InputField
            type="text"
            value={food.foodName}
            onChange={foodHandler}
            placeholder="eaten food"
          />
          <Button type="submit">save</Button>
        </form>
        <form className="form2">
          <InputField
            type="text"
            value=""
            onChange=""
            placeholder="experienced pain"
          />
          <Button type="submit">save</Button>
        </form>
      </Content>
      <Footer />
    </Wrapper>
  )
}

const Wrapper = styled.div`
  h1 {
    grid-column: 2;
    grid-row: 1;
  }

  .form1 {
    grid-column: 2;
    grid-row: 2;
    display: flex;
  }

  .form2 {
    grid-column: 2;
    grid-row: 3;
    display: flex;
  }
`
