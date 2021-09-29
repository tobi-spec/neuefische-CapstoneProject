import styled from "styled-components";
import {useAuth} from "../auth/AuthProvider";
import {resetPassword} from "../service/AxiosService";
import InputField from "./InputField";
import Button from "./Button";
import {useState} from "react";

export default function ResetPassword({cancelHandler}){
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

    const cancelButtonHandler = () => {
        cancelHandler()
    }

    return <Wrapper>
            <InputField
                placeholder="Reset Password"
                type="text"
                value={newPassword.newPassword}
                onChange={passwordHandler}
            />
            <Button onClick={passwordSubmitHandler}>send</Button>
            <Button onClick={cancelButtonHandler}>cancel</Button>
    </Wrapper>
}

const Wrapper = styled.form`
`