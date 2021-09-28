import {useAuth} from "../auth/AuthProvider";
import Button from "./Button";

export default function LogoutButton() {
    const { logout} = useAuth()

    const clickHandler = () =>{logout()}

    return <Button onClick={clickHandler}>Logout</Button>
    }

