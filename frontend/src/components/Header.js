import styled from 'styled-components'
import BackButton from "./BackButton";
import ProfileButton from "./ProfileButton";
import {useAuth} from "../auth/AuthProvider";

export default function Header({ title }) {
    const {user} = useAuth();

  return (
    <Wrapper>
        <BackButton/>
      <h1>{title}</h1>
        { user && <ProfileButton/>}
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
