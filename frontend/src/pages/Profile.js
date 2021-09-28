import styled from "styled-components";
import Header from "../components/Header";
import Content from "../components/Content";
import Footer from "../components/Footer";
import InputField from "../components/InputField";
import {useAuth} from "../auth/AuthProvider";
import {useState} from "react";
import Button from "../components/Button";
import {resetPassword, deleteAccount} from "../service/AxiosService";

export default function Profile(){
    const {token, logout} = useAuth()
    const [newPassword, setNewPassword] = useState({ newPassword: ""})

    const passwordHandler = event =>
        setNewPassword({
            newPassword: event.target.value
        })

    const passwordSubmitHandler = event => {
        event.preventDefault()
        resetPassword(newPassword, token)
            .then(response => console.log(response))
            .catch(error => console.error(error))
            .finally(() => setNewPassword({ newPassword: '' }))
    }

    const deleteAccountHandler = () => {
        deleteAccount(token)
            .then(response => console.log(response))
            .then(() => logout())
            .catch(error => console.error(error))
    }

    return (<Wrapper>
        <Header title="Profile"/>
        <Content>
            <form className="password">
                <InputField
                    title="Reset Password"
                    type="text"
                    value={newPassword.newPassword}
                    onChange={passwordHandler}
                />
                <Button onClick={passwordSubmitHandler}>send</Button>
            </form>
            <div className="delete">
                <Button onClick={deleteAccountHandler}>delete Account</Button>
            </div>
        </Content>
        <Footer/>
    </Wrapper>)
}

const Wrapper = styled.div`

    .password {
      grid-column: 2;
      grid-row: 2;
    }
  
  .delete {
    grid-column: 2;
    grid-row: 3;
  }
  
`