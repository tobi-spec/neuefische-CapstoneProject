import styled from 'styled-components'
import {NavLink, useLocation} from 'react-router-dom'
import {useAuth} from "../auth/AuthProvider";

export default function Footer() {
    const {user} = useAuth()
    const location = useLocation()

  return (
    <Wrapper>
        {user && location.pathname !== "/main" ? (<NavLink className="Link" to="/main">Notes</NavLink>) : null}
        {user && location.pathname !== "/fooddiary" ? (<NavLink className="Link" to="/fooddiary">Food Diary</NavLink>) : null}
        {user && location.pathname !== "/paindiary" ? (<NavLink className="Link" to="/paindiary">Pain Diary</NavLink>) : null}
        {user && location.pathname !== "/tracker" ? (<NavLink className="Link" to="/tracker">Perpetrator Tracker</NavLink>) : null}
    </Wrapper>
  )
}

const Wrapper = styled.footer`
  background-color: #138808;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  height: 50px;
  bottom: 0;
  left: 0;
  right: 0;

  .Link {
    font-size: 20px;
    text-decoration: none;
    color: black;
    margin: 5px;
    padding: 10px;
  }
`
