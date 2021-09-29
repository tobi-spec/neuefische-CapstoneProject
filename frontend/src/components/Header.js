import styled from 'styled-components'
import BackButton from "./BackButton";
import ProfileButton from "./ProfileButton";
import {useLocation} from "react-router-dom";
import LogoutButton from "./LogoutButton";

export default function Header({ title }) {
    const location = useLocation()

  return (
    <Wrapper>
        { location.pathname === "/main" ? <LogoutButton/> : <BackButton/>}
      <h1>{title}</h1>
        { location.pathname === "/main" ? <ProfileButton/> : null}
    </Wrapper>
  )
}

const Wrapper = styled.header`
  background-color: #138808;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  justify-items: center;
  align-items: center;
`
