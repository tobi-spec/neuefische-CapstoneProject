import styled from "styled-components";
import Header from "../components/Header";
import Content from "../components/Content";
import Footer from "../components/Footer";
import InputField from "../components/InputField";
import {useAuth} from "../auth/AuthProvider";
import {useState} from "react";
import Button from "../components/Button";
import {resetPassword} from "../service/AxiosService";

export default function Profile(){
    const {token} = useAuth()
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
            <p>delete data</p>
            <p>delete account</p>
        </Content>
        <Footer/>
    </Wrapper>)
}

const Wrapper = styled.div`

    .password { 
      grid-row: 2;
      grid-column: 2;
    }
`