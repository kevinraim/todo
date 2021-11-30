import React from "react";

class AuthenticationModal extends React.Component {
    render() {
        const showAuthenticationModal = this.props.showAuthenticationModal
        const login = this.props.login
        const register = this.props.register

        const validateInput = () => {
            let email = document.getElementById('email');
            let password = document.getElementById('password');

            if (!email.value || !password.value) {
                alert("Invalid email or password");
                return false;
            }
            return true;
        }

        const tryLogin = () => {
            if(!validateInput()){
                return;
            }
            let email = document.getElementById('email');
            let password = document.getElementById('password')
            login(email.value, password.value)
        }

        const tryRegister = () => {
            if(!validateInput()){
                return;
            }
            let email = document.getElementById('email');
            let password = document.getElementById('password')
            register(email.value, password.value)
        }

        if(showAuthenticationModal){
            return (
                <div className="modal fade show d-block bg-secondary" tabIndex="-1">
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title">Authenticate</h5>
                            </div>
                            <div className="modal-body">
                                <input type="text" id="email" className="form-control m-2" placeholder="Email" />
                                <input type="password" id="password" className="form-control m-2" placeholder="Password" />
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-outline-secondary m-2" onClick={() => { tryLogin() }}>Login</button>
                                <button type="button" className="btn btn-outline-secondary m-2" onClick={() => { tryRegister() }}>Register</button>
                            </div>
                        </div>
                    </div>
                </div>
    
            );
        }

        else
            return(<div></div>)
    }
}

export default AuthenticationModal;