import { ActionType, AddSubscription, AppState, PlayMessage, ChangeEpisode,
    ChangeHeaderText, ChangeSubscriptions, ChangeUser, StateActions, 
    SubscribedEpisode, SubscribedPodcast, SetAutoPlay, User, ChangePlayMessage } from "../Types";

export function stateReducer(state: AppState, action: StateActions): AppState {
    switch (action.type) {
        case ActionType.ChangePlayMessage:
            return { ...state, playMessage: action.payload};
        case ActionType.ChangeUser:
            return { ...state, currentUser: action.payload};
        case ActionType.ChangeEpisode:
            return { ...state, currentEpisode: action.payload};
        case ActionType.ChangeHeaderText:
            return { ...state, headerText: action.payload};
        case ActionType.AddSubscription:
            return { ...state, userSubscriptions: [...state.userSubscriptions, action.payload] };
        case ActionType.ChangeSubscriptions:
            return { ...state, userSubscriptions: action.payload};
        case ActionType.SetAutoPlay:
            return { ...state, autoPlay: action.payload};
        default:
        return state;
    }
}

export const changePlayMessage = (playMessage: PlayMessage): ChangePlayMessage => ({
    type: ActionType.ChangePlayMessage,
    payload: playMessage,
});

export const changeUser = (user: User): ChangeUser => ({
    type: ActionType.ChangeUser,
    payload: user,
});

export const changeEpisode = (episode: SubscribedEpisode): ChangeEpisode =>  ({
    type: ActionType.ChangeEpisode,
    payload: episode,
});

export const changeHeaderText = (text: string): ChangeHeaderText => ({
    type: ActionType.ChangeHeaderText,
    payload: text,
});

export const addSubscription = (subscription: SubscribedPodcast): AddSubscription => ({
    type: ActionType.AddSubscription,
    payload: subscription,
});

export const changeSubscriptions = (subscriptions: SubscribedPodcast[]): ChangeSubscriptions => ({
    type: ActionType.ChangeSubscriptions,
    payload: subscriptions,
});

export const setAutoPlay = (autoPlay: boolean): SetAutoPlay => ({
    type: ActionType.SetAutoPlay,
    payload: autoPlay,
});

