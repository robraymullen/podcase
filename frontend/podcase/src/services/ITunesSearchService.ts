export const search = (searchTerm: string) => {
    //ttps://itunes.apple.com/search?media=podcast&term=
    fetch(`https://itunes.apple.com/search?media=podcast&term=${searchTerm}`)
        .then(
            (response) => {
                if (!response.ok) {
                    throw new Error(response.statusText)
                }
                return response.json();
            }
        ).then(
            (json) => {
                console.log(json);
            }
        )
        .catch();
};
