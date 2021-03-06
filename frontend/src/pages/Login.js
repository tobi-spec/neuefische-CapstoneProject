import styled from 'styled-components'
import { useState } from 'react'
import { useAuth } from '../auth/AuthProvider'
import { Redirect } from 'react-router-dom'
import InputField from '../components/InputField'
import Button from '../components/Button'
import Content from '../components/Content'
import Header from '../components/Header'
import Footer from '../components/Footer'

export default function Login() {
  const { login, user } = useAuth()
  const [error, setError] = useState()
  const [credentials, setCredentials] = useState({
    userName: '',
    userPassword: '',
  })

  const credentialsHandler = event =>
    setCredentials({ ...credentials, [event.target.name]: event.target.value })

  const submitHandler = event => {
    event.preventDefault()
    login(credentials).catch(error => setError(error))
  }

  if (user) {
    return <Redirect to="/main" />
  }

  return (
    <Wrapper>
      <Header title="Login" />
      <Content>
        <form onSubmit={submitHandler}>
          <InputField
            placeholder="Username"
            type="Text"
            name="userName"
            value={credentials.userName}
            onChange={credentialsHandler}
          />
          <InputField
            placeholder="Password"
            type="Password"
            name="userPassword"
            value={credentials.userPassword}
            onChange={credentialsHandler}
          />
          <Button type="submit">Login</Button>
        </form>
        {error && <p className="error">{error.response.data.message}</p>}
      </Content>
      <Footer />
    </Wrapper>
  )
}

const Wrapper = styled.div`

  form {
    grid-column: 2;
    grid-row: 2;
  }
  
  .error {
    grid-column: 2;
    grid-row: 3;
  }
`
