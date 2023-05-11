package com.example.vix_schoters_newsapp

class NewsListFragment : Fragment() {
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var binding: FragmentNewsListBinding
    private lateinit var adapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        viewModel.getTopHeadlines("us")
    ): View? {
        binding = FragmentNewsListBinding.inflate(inflater, container, false)
        adapter = NewsListAdapter()
        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())

        viewModel.news.observe(viewLifecycleOwner) { articles ->
            adapter.submitList(articles)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTopHeadlines()
    }
}