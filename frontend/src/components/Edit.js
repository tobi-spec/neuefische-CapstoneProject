import InputField from './InputField'
import Button from './Button'
import styled from 'styled-components'
import { useState } from 'react'
import { useAuth } from '../auth/AuthProvider'

export default function Edit({ id, reloadList, cancelHandler, editService }) {
  const { token } = useAuth()
  const [newName, setNewName] = useState({ newName: '' })

  const nameHandler = event =>
    setNewName({
      newName: event.target.value,
    })

  const nameSubmitHandler = event => {
    event.preventDefault()
    editService(id, newName, token)
      .then(response => console.log(response))
      .then(() => reloadList(token))
      .then(error => console.error(error))
      .finally(() => setNewName(''))
  }

  return (
    <Wrapper onSubmit={nameSubmitHandler}>
      <InputField placeholder="new name" onChange={nameHandler} />
      <Button>send</Button>
    </Wrapper>
  )
}

const Wrapper = styled.form`
  display: flex;
`
