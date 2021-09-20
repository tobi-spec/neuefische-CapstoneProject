import { Route, BrowserRouter as Router, Switch } from 'react-router-dom'
import Main from './pages/Main'
import Login from './pages/Login'
import ProtectedRoute from './auth/ProtectedRoute'
import AuthProvider from './auth/AuthProvider'
import FoodDiary from './pages/FoodDiary'
import PainDiary from './pages/PainDiary'
import Welcome from "./pages/Welcome";
import Registration from "./pages/Registration";
function App() {
  return (
    <AuthProvider>
      <Router>
        <Switch>
          <Route exact path="/" component={Welcome} />
          <Route path="/login" component={Login} />
          <Route path="/registration" component={Registration} />
          <ProtectedRoute path="/main" component={Main} />
          <ProtectedRoute path="/fooddiary" component={FoodDiary} />
          <ProtectedRoute path="/paindiary" component={PainDiary} />
        </Switch>
      </Router>
    </AuthProvider>
  )
}

export default App
