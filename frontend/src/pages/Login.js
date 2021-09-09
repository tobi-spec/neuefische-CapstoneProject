import styled from 'styled-components'
import { useState } from 'react'
import { useAuth } from '../auth/AuthProvider'
import { Redirect } from 'react-router-dom'
import InputField from '../components/InputField'
import Button from '../components/Button'

export default function Login() {
  const { login, user } = useAuth()
  const [credentials, setCredentials] = useState({
    userName: '',
    userPassword: '',
  })

  const credentialsHandler = event =>
    setCredentials({ ...credentials, [event.target.name]: event.target.value })

  const submitHandler = event => {
    event.preventDefault()
    login(credentials).catch(error => console.error(error))
  }

  if (user) {
    return <Redirect to="/main" />
  }

  return (
    <Wrapper>
      <h1> Login </h1>
      <form onSubmit={submitHandler}>
        <InputField
          title="Username"
          type="Text"
          name="userName"
          value={credentials.userName}
          onChange={credentialsHandler}
        />
        <InputField
          title="Password"
          type="Text"
          name="userPassword"
          value={credentials.userPassword}
          onChange={credentialsHandler}
        />
        <Button type="submit">Login</Button>
      </form>
    </Wrapper>
  )
}

const Wrapper = styled.div`
  display: grid;
  height: 100vh;
  grid-template-rows: 1fr 1fr 1fr 1fr 1fr 1fr;
  grid-template-columns: 1fr 1fr 1fr;

  justify-content: center;
  justify-items: center;
  align-items: center;

  h1 {
    grid-column: 2;
    grid-row: 2;
  }

  form {
    grid-column: 2;
    grid-row: 3;
  }
`
