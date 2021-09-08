import styled from 'styled-components'
import { useState } from 'react'
import { useAuth } from '../auth/AuthProvider'

export default function Login() {
  const { login } = useAuth()
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

  return (
    <Wrapper onSubmit={submitHandler}>
      <h1> Login</h1>
      <input
        type="Text"
        name="userName"
        value={credentials.userName}
        onChange={credentialsHandler}
      />
      <input
        type="Text"
        name="userPassword"
        value={credentials.userPassword}
        onChange={credentialsHandler}
      />
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
