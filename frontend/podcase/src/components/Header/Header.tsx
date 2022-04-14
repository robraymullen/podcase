import AppBar from '@mui/material/AppBar';
import Typography from '@mui/material/Typography';
import InputBase from '@mui/material/InputBase';
import SearchIcon from '@mui/icons-material/Search';
import { Toolbar } from '@mui/material';
import { styled, alpha } from '@mui/material/styles';
import { KeyboardEvent, useContext, useEffect } from 'react';
import { search } from '../../services/ITunesSearchService';
import { AppContext } from '../../context/context';
import { getAllUsers } from '../../services/PodcaseAPIService';
import { ITunesResult, User } from '../../Types';
import { changeUser } from '../../context/reducer';
import { useNavigate } from "react-router-dom";

const Header = (props: any) => {

    const { state, dispatch } = useContext(AppContext);
    const navigate = useNavigate();

    useEffect(() => {
        if (state.currentUser == null) {
            getAllUsers((users: User[]) => {
                dispatch(changeUser(users[0]));
              }, () => { });
        }
    }, []);

    const doSearch = (event: KeyboardEvent) => {
        if (event.key === 'Enter') {
            // Cancel the default action, if needed
            const element = event.target as HTMLInputElement;
            const navigateToResults = (results: ITunesResult[]) => {
                // onClick={() => navigate("/", { state: { podcastState: GridRoutes.PODCAST_SUBSCRIPTION } })}
                navigate("/search", {state: {searchResult: results} });
            }
            search(element.value, navigateToResults, () => {console.log("error with search");});
            console.log(event);
        }

    }

    const drawerWidth = 240;

    const Search = styled('div')(({ theme }: any) => ({
        position: 'relative',
        borderRadius: theme.shape.borderRadius,
        backgroundColor: alpha(theme.palette.common.white, 0.15),
        '&:hover': {
            backgroundColor: alpha(theme.palette.common.white, 0.25),
        },
        marginLeft: 0,
        width: '100%',
        [theme.breakpoints.up('sm')]: {
            marginLeft: theme.spacing(1),
            width: 'auto',
        },
    }));

    const SearchIconWrapper = styled('div')(({ theme }) => ({
        padding: theme.spacing(0, 2),
        height: '100%',
        position: 'absolute',
        pointerEvents: 'none',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    }));

    const StyledInputBase = styled(InputBase)(({ theme }) => ({
        color: 'inherit',
        '& .MuiInputBase-input': {
            padding: theme.spacing(1, 1, 1, 0),
            // vertical padding + font size from searchIcon
            paddingLeft: `calc(1em + ${theme.spacing(4)})`,
            transition: theme.transitions.create('width'),
            width: '100%',
            [theme.breakpoints.up('sm')]: {
                width: '12ch',
                '&:focus': {
                    width: '20ch',
                },
            },
        },
    }));

    return (
        <AppBar
            position="fixed"
            sx={{ width: `calc(100% - ${drawerWidth}px)`, ml: `${drawerWidth}px` }}
        >
            <Toolbar>
                <Typography variant="h6" noWrap component="div" sx={{ flexGrow: 1, display: { xs: 'none', sm: 'block' } }}>
                    {state.headerText}
                </Typography>
                {
                    state.currentUser && state.currentUser.name ?
                        <Typography variant="h6" component="div" sx={{ paddingRight: "20px" }}>
                            {state.currentUser.name}
                        </Typography>
                    : <div></div>
                }

                <Search>
                    <SearchIconWrapper>
                        <SearchIcon />
                    </SearchIconWrapper>
                    <StyledInputBase
                        onKeyDown={doSearch}
                        placeholder="Search…"
                        inputProps={{ 'aria-label': 'search' }}
                    />
                </Search>
            </Toolbar>

        </AppBar>
    )
}

export default Header;
