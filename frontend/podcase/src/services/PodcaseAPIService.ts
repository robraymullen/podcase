import { Episode, Podcast, SubscribedPodcast, User } from "../Types";

export const getAllUsers = async(success: Function, error: Function) => {
    const url = `${process.env.REACT_APP_PODCASE_BASE_URL}users`;
    await fetch(url).then(response => {
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        return response.json() as unknown as User[];
    }).then(users => {
        success(users);
    }).catch(exception => {
        error(exception);
    });
};

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
        }).catch(exception => {
            error(exception);
        });
}

//TODO only return podcast and not its episodes
export const getPodcast = async (podcastId: number, success: Function, error: Function) => {
    const url = `${process.env.REACT_APP_PODCASE_BASE_URL}podcasts/${podcastId}`;
    await fetch(url).then(
        (response) => {
            if (!response.ok) {
                throw new Error(response.statusText)
            }
            return response.json() as unknown as Podcast;
        }
    ).then(podcast => {
        success(podcast);
    }).catch(exception => {
        error(exception);
    })
}

export const getAllPodcasts = async (success: Function, error: Function) => {
    const url = `${process.env.REACT_APP_PODCASE_BASE_URL}podcasts`;
    await fetch(url).then(
        (response) => {
            if (!response.ok) {
                throw new Error(response.statusText)
            }
            return response.json() as unknown as SubscribedPodcast[];
        }).then(podcasts => {
            success(podcasts);
        }).catch(exception => {
            error(exception);
        });
}

export const getPlaystate = async (userId: number, episodeId: number) => {

}

export const getPodcastEpisodes = async (podcastId: number, success: Function, error: Function) => {

    ///episodes/playstate/{podcastId}/user/{userId}
    const url = `${process.env.REACT_APP_PODCASE_BASE_URL}episodes/playstate/${podcastId}/user/1`;
    await fetch(url).then(
        (response) => {
            if (!response.ok) {
                throw new Error(response.statusText)
            }
            return response.json() as unknown as Episode[];
        }).then(episodes => {
            success(episodes);
        }).catch(exception => {
            error(exception);
        });
}

export const addPocast = async (url: string) => {

}

export const getMostRecentPlayedEpisode = async (success: Function, error: Function) => {
    ///episodes/recent
    const url = `${process.env.REACT_APP_PODCASE_BASE_URL}episodes/recent/user/1`;
    await fetch(url).then(
        (response) => {
            if (!response.ok) {
                throw new Error(response.statusText)
            }
            return response.json() as unknown as Episode;
        }).then(episode => {
            success(episode);
        }).catch(exception => {
            error(exception);
        });
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
