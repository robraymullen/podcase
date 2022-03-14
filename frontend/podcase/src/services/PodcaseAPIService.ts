import { SubscribedPodcast } from "../Types";



export const getUserSubscriptions = async (userId: number, success: Function, error: Function) => {
    const url = `${process.env.REACT_APP_PODCASE_BASE_URL}users/${userId}/subscriptions`;
    await fetch(url).then(
        (response) => {
            if (!response.ok) {
                throw new Error(response.statusText)
            }
            return response.json() as unknown as SubscribedPodcast[];
        }).then(podcasts => {
            success(podcasts);
        }).catch(exception =>{
            error(exception);
        });
}

export const getPlaystate = async (userId: number, episodeId: number) => {

}

export const getPodcastEpisodes = async (podcastId: number) => {

}

export const addPocast = async (url: string) => {

}


/**
 * Adds a podcast subscription from an RSS feed. This method should be used when 
 * adding a new podcast to the repository
 * @param url 
 */
export const addUserSubscriptionFromRSS = async (url: string) => {

}

/**
 * Add a user subscription from an existing podcast in the repository
 * @param podcastId 
 */
export const addUserSubscriptionFromExisting = async (podcastId: string) => {

}
