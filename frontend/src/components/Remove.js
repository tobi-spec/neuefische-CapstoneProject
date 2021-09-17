import Button from './Button'
import { removeFood } from '../service/AxiosService'
import { useAuth } from '../auth/AuthProvider'

export default function Remove({ id, reloadList }) {
  const { token } = useAuth()

  const yesHandler = () => {
    removeFood(id, token)
      .then(response => console.log(response))
      .catch(error => console.log(error))
  }

  const noHandler = () => {
    reloadList()
  }

  return (
    <section>
      <p>Sure?</p>
      <form onSubmit={yesHandler}>
        <Button>Yes</Button>
      </form>
      <form onSubmit={noHandler}>
        <Button>No</Button>
      </form>
    </section>
  )
}
