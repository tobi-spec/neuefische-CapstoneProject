import styled from 'styled-components'
import Button from './Button'
import Edit from './Edit'
import { useState } from 'react'

export default function FoodCard({ foodName, date, id }) {
  const [edit, setEdit] = useState(false)

  const editHandler = () => {
    setEdit(true)
  }

  const cancelHandler = () => {
    setEdit(false)
  }

  return (
    <Wrapper>
      <li>
        <p>food: {foodName}</p>
        <p>date: {date}</p>
        {edit && <Edit id={id} />}
        <Button onClick={editHandler}>Edit</Button>
        {!edit && <Button>Delete</Button>}
        {edit && <Button onClick={cancelHandler}>Cancel</Button>}
      </li>
    </Wrapper>
  )
}

const Wrapper = styled.div`
  display: flex;
  border-style: solid;
  border-radius: 10px;
  margin: 5px;
  padding: 3px;
`
