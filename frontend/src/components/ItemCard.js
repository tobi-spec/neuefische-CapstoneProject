import styled from 'styled-components'
import Button from './Button'
import Edit from './Edit'
import { useState } from 'react'
import Remove from './Remove'

export default function ItemCard({
  mainValue,
  id,
  valueTitle,
  reloadList,
  editService,
  removeService,
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
        <p>{valueTitle}: {mainValue}</p>
        {edit && (
          <Edit id={id}
                reloadList={reloadList}
                editService={editService}
                cancelHandler={cancelHandler} />
        )}
        {remove && (
          <Remove
            id={id}
            reloadList={reloadList}
            cancelHandler={cancelHandler}
            removeService={removeService}
          />
        )}
        {!edit && !remove && <Button onClick={editHandler}>edit</Button>}
        {!edit && !remove && <Button onClick={removeHandler}>remove</Button>}
        {edit && <Button onClick={cancelHandler}>Cancel</Button>}
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
