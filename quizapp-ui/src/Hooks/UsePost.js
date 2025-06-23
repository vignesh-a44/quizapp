const UsePost = (url) => {
    const postCall = async (params)=> {
        let result = {
            status: 'NO RESPONSE',
        };
        try {
            const param = {
                method: 'POST',
                headers: {
                    'content-type': 'application/json'
                },
                body: JSON.stringify(params)
            }
            const response = await fetch(url, param);
            result.response = await response.json();
            result.status = 'SUCCESS';
        } catch (err) {
            console.error(`Error while making POST call to ${url}: `,err);
            result.response = new Error('Something went wrong');
            result.status = 'SERVER ERROR';
        }
        return result;
    }
    return postCall;
}
 
export default UsePost;