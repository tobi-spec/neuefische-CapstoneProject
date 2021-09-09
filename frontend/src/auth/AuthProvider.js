import { useContext, useState } from 'react'
import jwt from 'jsonwebtoken'
import AuthContext from './AuthContext'
import { getToken } from '../service/AxiosService'

export default function AuthProvider({ children }) {
  const [token, setToken] = useState()

  const login = credentials =>
    getToken(credentials).then(token => setToken(token))

  const logout = () => setToken()

  const claims = jwt.decode(token)

  const user = claims && {
    username: claims.sub,
    role: claims.role,
  }

  return (
    <AuthContext.Provider value={{ token, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

export const useAuth = () => useContext(AuthContext)
