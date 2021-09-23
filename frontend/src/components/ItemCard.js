import styled from 'styled-components'
import Button from './Button'
import Edit from './Edit'
import { useState } from 'react'
import Remove from './Remove'

export default function ItemCard({
  mainValue,
  id,
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
        <p className="value">{mainValue}</p>
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
        {!edit && !remove && <Button onClick={editHandler}><img src="edit-interface-symbol.png" alt="detective"/></Button>}
        {!edit && !remove && <Button onClick={removeHandler}><img src="cross-remove-sign.png" alt="detective"/></Button>}
        {edit && <Button onClick={cancelHandler}>Cancel</Button>}
    </Wrapper>
  )
}

const Wrapper = styled.div`
  display: grid;
  grid-template-columns: min-content min-content;
  grid-template-rows: min-content min-content;
  justify-items: center;
  border-style: solid;
  border-radius: 10px;
  margin: 5px;
  padding: 3px;
  
  img {
    height: 15px;
  }
  .value {
    grid-column: span 2;
  }
`
