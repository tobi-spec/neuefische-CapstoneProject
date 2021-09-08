import { Route, BrowserRouter as Router, Switch } from 'react-router-dom'
import Main from './pages/Main'
import Login from './pages/Login'
import ProtectedRoute from './auth/ProtectedRoute'
import AuthProvider from './auth/AuthProvider'

function App() {
  return (
    <AuthProvider>
      <Router>
        <Switch>
          <Route exact path="/" component={Login} />
          <ProtectedRoute path="/main" component={Main} />
        </Switch>
      </Router>
    </AuthProvider>
  )
}

export default App
