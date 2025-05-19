import React from 'react'

export default function Login() {
  return (
    <div className='login-comp'>
        <div className='username-sec'>
            <input type='text' id='username_in' placeholder='Your email'></input>
        </div>
        <div className='password-sec'>
            <input type='password' id='password_in' placeholder='Enter password'></input>
        </div>
        <div className='signin-sec'>
            <button type='submit' id='submit_but'>Sign in</button>
            <button type='submit' id='forget_pass'>Forgot password?</button>
        </div>
        <div className='create-account-sec'>
            <button type='submit' id='create_acc'>Create Account</button>
        </div>
    </div>
  )
}