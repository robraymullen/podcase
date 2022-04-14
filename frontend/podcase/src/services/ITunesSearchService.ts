import { ITunesResult } from "../Types";

export const search = (searchTerm: string, success: Function, error: Function) => {
    //ttps://itunes.apple.com/search?media=podcast&term=
    fetch(`https://itunes.apple.com/search?media=podcast&term=${searchTerm}`)
        .then((response) => {
                if (!response.ok) {
                    throw new Error(response.statusText)
                }
                return response.json() as unknown as ITunesResult[];
        }).then((searchResults) => {
                success(searchResults);
        })
        .catch((exception) => {
            error(exception);
        });
};
