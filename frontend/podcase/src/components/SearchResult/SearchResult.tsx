import { List, Box, CircularProgress } from '@mui/material';
import { ITunesResult, SearchResponse } from "../../Types";
import SearchResultItem from "./SearchResultItem";
import { useNavigate, useLocation } from "react-router-dom";

interface Location {
    state: {
        searchResult: SearchResponse
    }
}

const SearchResult = () => {

    const location = useLocation() as Location;
    const searchResults = location.state && location.state.searchResult ? location.state.searchResult.results : [];
    console.log(location);

    return (
        <Box>
            {
                searchResults && searchResults.length > 0 ?
                    <div>
                        <List sx={{ bgcolor: 'background.paper' }}>
                            {
                                searchResults.map((result: ITunesResult) => {
                                    return <SearchResultItem key={result.collectionId+""} 
                                        item={result}
                                    >
                                    </SearchResultItem>
                                })
                            }
                        </List>
                    </div>

                    : <CircularProgress />
            }
        </Box>
    )
}

export default SearchResult;