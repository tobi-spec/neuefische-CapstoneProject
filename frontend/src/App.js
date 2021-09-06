import { Route, Router, Switch } from 'react-router-dom'

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/main">
          <Main />
        </Route>
      </Switch>
    </Router>
  )
}

export default App
