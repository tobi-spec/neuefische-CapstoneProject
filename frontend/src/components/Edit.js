import InputField from './InputField'
import Button from './Button'
import styled from 'styled-components'
import { useState } from 'react'
import { useAuth } from '../auth/AuthProvider'

export default function Edit({ id, reloadList, editService, cancelHandler }) {
  const { token } = useAuth()
  const [newName, setNewName] = useState({ newValue: '' })

  const nameHandler = event =>
    setNewName({
      newValue: event.target.value,
    })

  const nameSubmitHandler = event => {
    event.preventDefault()
    editService(id, newName, token)
      .then(response => console.log(response))
      .then(() => reloadList(token))
      .then(() => cancelHandler())
      .then(error => console.error(error))
      .finally(() => setNewName({newValue: ''}))
  }

  return (
    <Wrapper onSubmit={nameSubmitHandler}>
      <InputField placeholder="new" onChange={nameHandler} />
      <Button>send</Button>
    </Wrapper>
  )
}

const Wrapper = styled.form`
  display: flex;
`
