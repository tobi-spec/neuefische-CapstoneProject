import Button from './Button'
import { useAuth } from '../auth/AuthProvider'

export default function Remove({
  id,
  reloadList,
  cancelHandler,
  removeService,
}) {
  const { token } = useAuth()

  const yesHandler = () => {
    removeService(id, token)
      .then(response => console.log(response))
      .then(() => reloadList(token))
      .catch(error => console.log(error))
  }

  const noHandler = () => {
    cancelHandler()
  }

  return (
    <section>
      <p>Are you Sure?</p>
      <Button onClick={yesHandler}> Yes </Button>
      <Button onClick={noHandler}> No </Button>
    </section>
  )
}
