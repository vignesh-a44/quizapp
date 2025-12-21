import React, { useState } from 'react'
import UsePost from '../Hooks/UsePost'

export default function Login() {
    
    const [loginId, setLoginId] = useState('');
    const [password, setPassword] = useState('');
    const postCall = UsePost("http://localhost:8080/login");

    const login = async()=> {
        const credentials = {
            email: loginId,
            password: password
        }
        console.log('::: credentials: ',credentials);
        let user = await postCall(credentials);
        console.log('::: results: ',user);
    }

    return (
        <div className='login-comp'>
            <div className='username-sec'>
                <input type='text' id='username_in' onChange={ (e)=>{ setLoginId(e?.target?.value) } } placeholder='Your email'></input>
            </div>
            <div className='password-sec'>
                <input type='password' id='password_in'  onChange={ (e)=>{ setPassword(e?.target?.value) } } placeholder='Enter password'></input>
            </div>
            <div className='signin-sec'>
                <button type='submit' id='submit_but' onClick={ (e)=>{ e.preventDefault(); login() } }>Sign in</button>
                <button type='submit' id='forget_pass'>Forgot password?</button>
            </div>
            <div className='create-account-sec'>
                <button type='submit' id='create_acc'>Create Account</button>
            </div>
        </div>
    )
}