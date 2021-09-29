import Button from "./Button";
import {deleteAccount} from "../service/AxiosService";
import styled from "styled-components";
import {useAuth} from "../auth/AuthProvider";

export default function DeleteAccount({cancelHandler}){
    const {token, logout} = useAuth()

    const deleteAccountHandler = () => {
        deleteAccount(token)
            .then(response => console.log(response))
            .then(() => logout())
            .catch(error => console.error(error))
    }

    const noHandler = () => {
        cancelHandler()
    }

    return(<Wrapper>
            <p>Do you really want to remove your account?</p>
            <Button onClick={deleteAccountHandler}>Yes</Button>
            <Button onClick={noHandler}>No</Button>
        </Wrapper>
    )
}

const Wrapper = styled.div `
`