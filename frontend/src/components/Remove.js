import Button from './Button'
import { removeFood } from '../service/AxiosService'
import { useAuth } from '../auth/AuthProvider'
import { Redirect } from 'react-router-dom'
import { useEffect } from 'react'

export default function Remove({ id, reloadList, cancelHandler }) {
  const { token } = useAuth()

  const yesHandler = () => {
    removeFood(id, token)
      .then(response => console.log(response))
      .then(() => reloadList(token))
      .catch(error => console.log(error))
  }

  const noHandler = () => {
    cancelHandler()
  }

  return (
    <section>
      <p>Sure?</p>
      <Button onClick={yesHandler}> Yes </Button>
      <Button onClick={noHandler}> No </Button>
    </section>
  )
}
