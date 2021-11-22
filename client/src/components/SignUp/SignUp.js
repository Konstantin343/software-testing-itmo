import BaseComponent from "../Base/BaseComponent";
import React from "react";

export default class SignUp extends BaseComponent {
    constructor(props) {
        super(props);

        this.state = {
            login: '',
            password: ''
        }

        this.openSignInPage = this.openSignInPage.bind(this)
        this.changeLogin = this.changeLogin.bind(this)
        this.changePassword = this.changePassword.bind(this)
    }

    changeLogin(event) {
        this.setState({
            login: event.target.value
        });
    }

    changePassword(event) {
        this.setState({
            password: event.target.value
        });
    }

    openSignInPage() {
        window.location.href = '/sign-in'
    }

    render() {
        return (
            <div>
                <center>
                    <div className="w-25 mt-5">
                        <form method="post" name="sign-up" autoComplete="off"
                              action={process.env.REACT_APP_SERVER_HOST + ":" + process.env.REACT_APP_SERVER_PORT + "/sign-up"}>
                            <div className="w-100">
                                <div className={"label"}>Login</div>
                                <input type="text" name="login" className="form-control" placeholder="Login"/>
                            </div>
                            <div className="w-100">
                                <div className={"label"}>Password</div>
                                <input type="password" name="password" className="form-control" placeholder="Password"/>
                            </div>

                            <div className="login-button-holder w-50 mt-3">
                                <input type="submit" className={"login-button"} value="Sign up"/>
                            </div>
                            <input type="hidden" name="redirectTo"
                                   value={window.location.protocol + "//" + window.location.host + "/"}/>
                        </form>
                        <div className={"register-button-holder w-50"}>
                            <button className={"register-button"} onClick={this.openSignInPage}>Sign in</button>
                        </div>
                    </div>
                </center>
            </div>
        )
    }
}