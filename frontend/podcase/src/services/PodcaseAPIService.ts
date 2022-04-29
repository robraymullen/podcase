import { ResponseError } from "../Error/ResponseError";
import { Episode, PlayState, Podcast, SubscribedEpisode, SubscribedPodcast, User, DeadDownload } from "../Types";

export const getAllUsers = async (success: Function, error: Function) => {
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

export const getPodcastEpisodes = async (userId: number, podcastId: number, success: Function, error: Function) => {

    ///episodes/playstate/{podcastId}/user/{userId}
    const url = `${process.env.REACT_APP_PODCASE_BASE_URL}episodes/playstate/${podcastId}/user/${userId}`;
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

export const updateLastPlayed = async (userId: number, episode: SubscribedEpisode, success: Function) => {
    const url = `${process.env.REACT_APP_PODCASE_BASE_URL}playstate/`;
    const payload: PlayState = {
        userId: userId,
        episodeId: episode.id,
        playLength: episode.play_length,
        lastPlayed: Date.now(),
        id: null,
    }
    if (episode.play_state_id) {
        payload.id = episode.play_state_id;
    }
    await fetch(url, {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    }).then((response) => {
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        return response.json() as unknown as PlayState;
    }).then((playState) => {
        success(playState);
    }).catch((exception) => {
        console.error(exception);
    });
}

export const getNextEpisode = async (episodeId: number, userId: number, success: Function, error: Function) => {
    const url = `${process.env.REACT_APP_PODCASE_BASE_URL}episodes/${episodeId}/next/user/${userId}`;
    await fetch(url).then(
        (response) => {
            if (!response.ok) {
                throw new ResponseError(response);
            }
            return response.json() as unknown as SubscribedEpisode;
        }).then(episode => {
            success(episode);
        }).catch(exception => {
            error(exception);
        });
}

export const getMostRecentPlayedEpisode = async (userId: number, success: Function, error: Function) => {
    ///episodes/recent
    const url = `${process.env.REACT_APP_PODCASE_BASE_URL}episodes/recent/user/${userId}`;
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


export const addUser = async (name: string, password: string) => {
    const url = `${process.env.REACT_APP_PODCASE_BASE_URL}users/add`;
    const payload = {
        name: name,
        password: password,
    }
    await fetch(url, {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    });
}

/**
 * Adds a podcast subscription from an RSS feed. This method should be used when 
 * adding a new podcast to the repository
 * @param url 
 */
export const addUserSubscriptionFromRSS = async (podcastUrl: string, podcastName: string, userName: string, success?: Function, error?: Function) => {
    const url = `${process.env.REACT_APP_PODCASE_BASE_URL}podcasts/subscription`;
    const payload = {
        podcastUrl: podcastUrl,
        podcastName: podcastName,
        userName: userName,
    }
    await fetch(url, {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    }).then(response => {
        if (!response.ok) {
            throw new ResponseError(response);
        } else if(success) {
            success(response);
        }
    }).catch(exception =>{
        error && error !== undefined ? error(exception) : console.error(exception);
    });
}

export const getDeadDownloads = async (success: Function, error: Function) => {
    const url = `${process.env.REACT_APP_PODCASE_BASE_URL}deadDownloads`;
    await fetch(url).then(response => {
        if (!response.ok) {
            throw new ResponseError(response);
        }
        return response.json() as unknown as DeadDownload[];
    }).then((deadDownloads) => {
        success(deadDownloads);
    }).catch((exception) => {
        error(exception);
    })
};
