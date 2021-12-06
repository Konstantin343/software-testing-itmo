import BaseComponent from "../Base/BaseComponent";
import React from "react";

export default class SignIn extends BaseComponent {
    constructor(props) {
        super(props);

        this.state = {
            login: '',
            password: ''
        }

        this.openSignUpPage = this.openSignUpPage.bind(this)
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

    openSignUpPage() {
        window.location.href = '/sign-up'
    }

    render() {
        return (
            <div>
                <center>
                    <div className="w-25 mt-5">
                        <form method="post" name="sign-in" autoComplete="off"
                              action={process.env.REACT_APP_SERVER_HOST + ":" + process.env.REACT_APP_SERVER_PORT + "/sign-in"}>
                            <div className="w-100">
                                <div className={"label"}><label htmlFor="login">Login</label></div>
                                <input type="text" id="login" name="login" className="form-control" placeholder="Login"/>
                            </div>
                            <div className="w-100">
                                <div className={"label"}><label htmlFor="password">Password</label></div>
                                <input type="password" id="password" name="password" className="form-control" placeholder="Password"/>
                            </div>

                            <div className="login-button-holder w-50 mt-3">
                                <input type="submit" className={"login-button"} value="Sign in"/>
                            </div>
                            <input type="hidden" name="redirectTo"
                                   value={window.location.protocol + "//" + window.location.host + "/"}/>
                        </form>
                        <div className={"register-button-holder w-50"}>
                            <button className={"register-button"} onClick={this.openSignUpPage}>Sign up</button>
                        </div>
                    </div>
                </center>
            </div>
        )
    }
}