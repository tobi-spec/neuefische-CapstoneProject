import Button from './Button'
import { removeFood } from '../service/AxiosService'
import { useAuth } from '../auth/AuthProvider'

export default function Remove({ id }) {
  const { token } = useAuth()

  const yesHandler = () => {
    removeFood(id, token)
      .then(response => console.log(response))
      .catch(error => console.log(error))
  }

  return (
    <section>
      <p>Sure?</p>
      <Button onClick={yesHandler}>Yes</Button>
      <Button>No</Button>
    </section>
  )
}
