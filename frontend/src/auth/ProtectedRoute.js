import { useAuth } from './AuthProvider'
import { Redirect, Route } from 'react-router-dom'

export default function ProtectedRoute({ ...props }) {
  const { user } = useAuth()

  if (!user) {
    return <Redirect path="/" />
  }

  return <Route {...props} />
}
