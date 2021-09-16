import styled from 'styled-components'
import Button from './Button'
import Edit from './Edit'
import { useState } from 'react'
import Remove from './Remove'

export default function ItemCard({
  valueTitle,
  mainValue,
  date,
  id,
  renderHandler,
}) {
  const [edit, setEdit] = useState(false)
  const [remove, setRemove] = useState(false)

  const editHandler = () => {
    setEdit(true)
  }

  const removeHandler = () => {
    setRemove(true)
  }

  const cancelHandler = () => {
    setEdit(false)
    setRemove(false)
  }

  return (
    <Wrapper>
      <li>
        <p>
          {valueTitle}: {mainValue}
        </p>
        <p>date: {date}</p>
        {edit && <Edit id={id} />}
        {remove && <Remove id={id} renderHandler={renderHandler} />}
        {!edit && !remove && <Button onClick={editHandler}>edit</Button>}
        {!edit && !remove && <Button onClick={removeHandler}>remove</Button>}
        {(edit || remove) && <Button onClick={cancelHandler}>Cancel</Button>}
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
